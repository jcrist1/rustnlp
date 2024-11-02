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

public class BoxErr {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class BoxErrCleaner implements Runnable {

        MemorySegment segment;
        BoxErrCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.BoxErr_destroy(this.segment);
        }
    }

    BoxErr() {}
    BoxErr(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    
    public String get() {
        
        try (var arena = Arena.ofConfined()) {
            
            
            var nativeVal = rustnlp_h.BoxErr_get(arena, internal);
            
            var returnVal = SliceUtils.readUtf8(nativeVal);
            return returnVal;
                    
        }
    }
    
}