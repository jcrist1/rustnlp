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

public class PairSeq {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class PairSeqCleaner implements Runnable {

        MemorySegment segment;
        PairSeqCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.PairSeq_destroy(this.segment);
        }
    }

    PairSeq() {}
    PairSeq(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    
    public int[] getSlice() {
        
        try (var arena = Arena.ofConfined()) {
            
            
            var nativeVal = rustnlp_h.PairSeq_get_slice(arena, internal);
            
            var data = dev.gigapixel.rustnlp.ntv.DiplomatI32View.data(nativeVal);
            var len = dev.gigapixel.rustnlp.ntv.DiplomatI32View.len(nativeVal);
            var returnVal = SliceUtils.intSliceToArray(nativeVal);
            return returnVal;
                    
        }
    }
    
}