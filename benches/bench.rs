extern crate rustnlp;
use std::{fs::File, io::Read};

use criterion::{black_box, criterion_group, criterion_main, Criterion};
pub fn bench_offsets(c: &mut Criterion) {
    use rustnlp::ffi::Tok;
    let tok = Tok::create();
    let lipsum = include_str!("lipsum.txt");
    c.bench_function("tokenize offsets", |b| {
        b.iter(|| black_box(tok.tokenize_as_offsets(lipsum)))
    });
}

pub fn bench_tokenize(c: &mut Criterion) {
    use rustnlp::ffi::Tok;
    let tok = Tok::create();
    let lipsum = include_str!("lipsum.txt");

    c.bench_function("tokenize", |b| b.iter(|| black_box(tok.tokenize(lipsum))));
}

pub fn bench_whitespace_tokenize(c: &mut Criterion) {
    use rustnlp::ffi::Tok;
    let tok = Tok::create();
    let lipsum = include_str!("lipsum.txt");

    let r = regex::Regex::new("\\s\\+").unwrap();
    let mut lipsum = String::new();
    let file = File::open("benches/lipsum.txt")
        .expect("done")
        .read_to_string(&mut lipsum)
        .expect("failed to read file");
    let split = |text: &str| {
        r.split(text)
            .map(|s| s.to_string())
            .collect::<Vec<String>>();
    };

    c.bench_function("whitespace tokenize", |b| {
        b.iter(|| black_box(split((&lipsum))))
    });
}

criterion_group!(
    benches,
    bench_tokenize,
    bench_offsets,
    // bench_whitespace_tokenize
);
criterion_main!(benches);
