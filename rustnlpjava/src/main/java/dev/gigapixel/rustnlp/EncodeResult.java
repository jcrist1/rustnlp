package dev.gigapixel.rustnlp;

import dev.gigapixel.rustnlp.ntv.*;


import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.ref.Cleaner;
import java.util.List;
import static java.lang.foreign.ValueLayout.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class EncodeResult {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class EncodeResultCleaner implements Runnable {

        MemorySegment segment;
        EncodeResultCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.EncodeResult_destroy(this.segment);
        }
    }

    EncodeResult() {}
    EncodeResult(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    
    public TokenSpan getByIdx(int i) {
        
        try (var arena = Arena.ofConfined()) {
            
            
            var iNative = i;
            var nativeVal = rustnlp_h.EncodeResult_get_by_idx(arena, internal, iNative);
            
            var returnVal = new TokenSpan(nativeVal);
            return returnVal;
                    
        }
    }
    
    public int len() {
        
        
        var nativeVal = rustnlp_h.EncodeResult_len(internal);
        
        var returnVal = nativeVal;
        return returnVal;
                
    }
    
    public boolean isEmpty() {
        
        
        var nativeVal = rustnlp_h.EncodeResult_is_empty(internal);
        
        var returnVal = nativeVal;
        return returnVal;
                
    }
    
    public PairSeq pairSeq() {
        
        
        var nativeVal = rustnlp_h.EncodeResult_pair_seq(internal);
        
        List<Object> selfEdges = List.of();
        
        
        
        var returnVal = new PairSeq(nativeVal, selfEdges);
        return returnVal;
                
    }
    
}