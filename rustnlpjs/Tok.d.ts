// generated by diplomat-tool
import type { TokRes } from "./TokRes"
import type { pointer, codepoint } from "./diplomat-runtime.d.ts";

export class Tok {
    

    get ffiValue(): pointer;

    tokenize(text: string): TokRes;
}