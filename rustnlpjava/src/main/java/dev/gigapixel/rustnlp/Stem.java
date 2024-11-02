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

public class Stem {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class StemCleaner implements Runnable {

        MemorySegment segment;
        StemCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.Stem_destroy(this.segment);
        }
    }

    Stem() {}
    Stem(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    public static Stem create() {
        
        var nativeVal = rustnlp_h.Stem_create();
        
        List<Object> selfEdges = List.of();
        
        
        
        var returnVal = new Stem(nativeVal, selfEdges);
        return returnVal;
                
    }
    
    
}