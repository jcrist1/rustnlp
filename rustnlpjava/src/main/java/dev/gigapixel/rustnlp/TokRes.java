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

public class TokRes {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class TokResCleaner implements Runnable {

        MemorySegment segment;
        TokResCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.TokRes_destroy(this.segment);
        }
    }

    TokRes() {}
    TokRes(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    
    public String getByIdx(int i) {
        
        try (var arena = Arena.ofConfined()) {
            
            
            var iNative = i;
            var nativeVal = rustnlp_h.TokRes_get_by_idx(arena, internal, iNative);
            
            var returnVal = SliceUtils.readUtf8(nativeVal);
            return returnVal;
                    
        }
    }
    
    public int len() {
        
        
        var nativeVal = rustnlp_h.TokRes_len(internal);
        
        var returnVal = nativeVal;
        return returnVal;
                
    }
    
    public boolean isEmpty() {
        
        
        var nativeVal = rustnlp_h.TokRes_is_empty(internal);
        
        var returnVal = nativeVal;
        return returnVal;
                
    }
    
}