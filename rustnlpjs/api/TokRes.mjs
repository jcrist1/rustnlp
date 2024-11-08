// generated by diplomat-tool
import wasm from "./diplomat-wasm.mjs";
import * as diplomatRuntime from "./diplomat-runtime.mjs";

const TokRes_box_destroy_registry = new FinalizationRegistry((ptr) => {
    wasm.TokRes_destroy(ptr);
});

export class TokRes {
    // Internal ptr reference:
    #ptr = null;

    // Lifetimes are only to keep dependencies alive.
    // Since JS won't garbage collect until there are no incoming edges.
    #selfEdge = [];
    
    constructor(symbol, ptr, selfEdge) {
        if (symbol !== diplomatRuntime.internalConstructor) {
            console.error("TokRes is an Opaque type. You cannot call its constructor.");
            return;
        }
        
        this.#ptr = ptr;
        this.#selfEdge = selfEdge;
        
        // Are we being borrowed? If not, we can register.
        if (this.#selfEdge.length === 0) {
            TokRes_box_destroy_registry.register(this, this.#ptr);
        }
    }

    get ffiValue() {
        return this.#ptr;
    }

    get(i) {
        const diplomatReceive = new diplomatRuntime.DiplomatReceiveBuf(wasm, 9, 4, true);
        
        // This lifetime edge depends on lifetimes 'a
        let aEdges = [this];
        
        const result = wasm.TokRes_get(diplomatReceive.buffer, this.ffiValue, i);
    
        try {
            if (!diplomatReceive.resultFlag) {
                return null;
            }
            return new diplomatRuntime.DiplomatSliceStr(wasm, diplomatReceive.buffer,  "string8", aEdges);
        }
        
        finally {
            diplomatReceive.free();
        }
    }

    getByIdx(i) {
        const diplomatReceive = new diplomatRuntime.DiplomatReceiveBuf(wasm, 8, 4, false);
        
        // This lifetime edge depends on lifetimes 'a
        let aEdges = [this];
        
        const result = wasm.TokRes_get_by_idx(diplomatReceive.buffer, this.ffiValue, i);
    
        try {
            return new diplomatRuntime.DiplomatSliceStr(wasm, diplomatReceive.buffer,  "string8", aEdges);
        }
        
        finally {
            diplomatReceive.free();
        }
    }

    len() {
        const result = wasm.TokRes_len(this.ffiValue);
    
        try {
            return result;
        }
        
        finally {}
    }

    isEmpty() {
        const result = wasm.TokRes_is_empty(this.ffiValue);
    
        try {
            return result;
        }
        
        finally {}
    }
}