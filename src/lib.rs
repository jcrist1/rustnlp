#[diplomat::bridge]
pub mod ffi {
    use core::str;

    use diplomat_runtime::DiplomatUtf8StrSlice;
    use tokenizers::{Encoding, Model, Tokenizer};

    #[diplomat::opaque]
    pub struct Tok(pub(crate) Tokenizer);

    #[diplomat::opaque]
    pub struct TokRes(Encoding);

    #[diplomat::opaque]
    pub struct Strr(str);

    #[diplomat::opaque]
    pub struct BoxErr(String);

    impl BoxErr {
        pub fn get<'a>(&'a self) -> &'a str {
            self.0.as_ref()
        }
    }

    impl Tok {
        pub fn create() -> Box<Self> {
            let json_bytes = include_bytes!("tokenizer.json");
            let tkz =
                Tokenizer::from_bytes(json_bytes).expect("Failed to decode tokenizer json bytes");
            Box::new(Tok(tkz))
        }

        pub fn tokenize(&self, text: &str) -> Result<Box<TokRes>, Box<BoxErr>> {
            let encode = self
                .0
                .encode_fast(text, false)
                .map_err(|err| Box::new(BoxErr(err.to_string())))?;
            Ok(Box::new(TokRes(encode)))
        }
        pub fn tokenize_as_offsets(&self, text: &str) -> Result<Box<EncodeResult>, Box<BoxErr>> {
            let enc = self
                .0
                .encode_char_offsets(text, true)
                .map_err(|err| Box::new(BoxErr(err.to_string())))?;
            let offsets = enc.get_offsets();
            let (chars_len_16, chars_len_8) = text
                .chars()
                .map(|char| (char.len_utf16(), char.len_utf8()))
                .unzip::<_, _, Vec<_>, Vec<_>>();
            let mut last_char_idx = 0;
            let mut curr_offset_16: usize = 0;
            let mut offsets_16 = Vec::with_capacity(offsets.len());
            let mut curr_offset_8: usize = 0;
            let mut offsets_8 = Vec::with_capacity(offsets.len());
            for (start, end) in offsets {
                let start_16 =
                    curr_offset_16 + chars_len_16[last_char_idx..*start].iter().sum::<usize>();
                let start_8 =
                    curr_offset_8 + chars_len_8[last_char_idx..*start].iter().sum::<usize>();
                let end_16 = start_16 + chars_len_16[*start..*end].iter().sum::<usize>();
                let end_8 = start_8 + chars_len_8[*start..*end].iter().sum::<usize>();
                last_char_idx = *end;
                curr_offset_16 = end_16;
                curr_offset_8 = end_8;
                offsets_16.push((start_16, end_16));
                offsets_8.push((start_8, end_8));
            }

            Ok(Box::new(EncodeResult {
                offsets: offsets.to_owned(),
                offsets_16,
                offsets_8,
            }))
        }
    }

    impl TokRes {
        #[diplomat::attr(auto, indexer)]
        pub fn get<'a>(&'a self, i: i32) -> Option<&'a str> {
            self.0.get_tokens().get(i as usize).map(AsRef::as_ref)
        }
    }

    use rust_stemmers::{Algorithm, Stemmer};

    #[diplomat::opaque]
    struct Stem(Stemmer);

    #[diplomat::opaque]
    struct Strings(Vec<String>);

    /// Idea should be to split a sentence into tokens
    /// We probably need the stem the code point index, the utf8 byte index and the utf16 index
    impl Stem {
        pub fn create() -> Box<Stem> {
            let en = Algorithm::English;
            let stemmer = Stemmer::create(en);
            Box::new(Stem(stemmer))
        }

        pub fn stem(&self, strs: &[DiplomatUtf8StrSlice]) -> Box<Strings> {
            Box::new(Strings(
                strs.iter()
                    .map(|s| {
                        let str_slice = s.as_ref();
                        self.0.stem(str_slice).to_string()
                    })
                    .collect(),
            ))
        }
    }

    #[diplomat::opaque]
    pub struct EncodeResult {
        // Token boundaries as utf code points
        offsets: Vec<(usize, usize)>,
        // Token boundaries as indexes of utf16 encoded data
        offsets_16: Vec<(usize, usize)>,
        // Token boundaries as indexes of utf8 encoded data
        offsets_8: Vec<(usize, usize)>,
    }

    pub struct TokenSpan {
        pub start: usize,
        pub end: usize,
    }

    impl EncodeResult {
        #[diplomat::attr(auto, indexer)]
        pub fn get(&self, i: i32) -> Option<TokenSpan> {
            self.offsets_16
                .get(i as usize)
                .copied()
                .map(|(start, end)| TokenSpan { start, end })
        }
    }
}

#[cfg(test)]
mod test {
    use tokenizers::NormalizedString;

    use crate::ffi::Tok;

    #[test]
    fn test_tok() {
        let text = "what is 🔥🔥 good for?";
        let tok = Tok::create();
        let mut normalized_text = NormalizedString::default();
        normalized_text.append(text);

        let enc = tok.0.encode_char_offsets(text, true).expect("failed");
        let offsets = enc.get_offsets();
        println!("Offsets: {offsets:?}");
        println!("{}", normalized_text.get_original());
        let chars_len_16 = text
            .chars()
            .map(|char| char.len_utf16())
            .collect::<Vec<_>>();
        let mut last_char_idx = 0;
        let mut curr_offset_16: usize = 0;
        let mut utf16_offsets = Vec::with_capacity(offsets.len());
        for (start, end) in offsets {
            let start_16 =
                curr_offset_16 + chars_len_16[last_char_idx..*start].iter().sum::<usize>();
            let end_16 = start_16 + chars_len_16[*start..*end].iter().sum::<usize>();
            last_char_idx = *end;
            curr_offset_16 = end_16;
            utf16_offsets.push((start_16, end_16));
        }
        let text_16 = text.encode_utf16().collect::<Vec<_>>();
        for ((start_16, end_16), (start, end)) in utf16_offsets.iter().zip(offsets.iter()) {
            let tok_16 = String::from_utf16_lossy(&text_16[*start_16..*end_16]);
            let tok = text
                .chars()
                .skip(*start)
                .take(end - start)
                .collect::<String>();
            assert_eq!(tok, tok_16);
        }
        let enc = tok.0.encode(text, true).expect("failed");
        let offsets = enc.get_offsets();
        println!("Offsets: {offsets:?}");
    }
}
