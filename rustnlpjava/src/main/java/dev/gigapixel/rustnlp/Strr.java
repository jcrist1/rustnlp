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

public class Strr {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class StrrCleaner implements Runnable {

        MemorySegment segment;
        StrrCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.Strr_destroy(this.segment);
        }
    }

    Strr() {}
    Strr(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    
}