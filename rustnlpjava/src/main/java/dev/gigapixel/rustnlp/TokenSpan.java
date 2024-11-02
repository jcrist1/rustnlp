package dev.gigapixel.rustnlp;

import dev.gigapixel.rustnlp.ntv.*;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.ref.Cleaner;
import java.lang.foreign.SegmentAllocator;
import java.util.List;
import static java.lang.foreign.ValueLayout.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class TokenSpan {
    long start;
    long end;
    

    List<Object> selfEdges = List.of();
    

    private TokenSpan() {
    }

    TokenSpan(MemorySegment structSegment) {
        this.selfEdges = selfEdges;
        

        var startNative = dev.gigapixel.rustnlp.ntv.TokenSpan.start(structSegment);
        var startVal = startNative;
        this.start = startVal;
        var endNative = dev.gigapixel.rustnlp.ntv.TokenSpan.end(structSegment);
        var endVal = endNative;
        this.end = endVal;
        

    }

    MemorySegment toNative(SegmentAllocator arena) {
        var returnVal = dev.gigapixel.rustnlp.ntv.TokenSpan.allocate(arena);
        
        var startNative = start;
        dev.gigapixel.rustnlp.ntv.TokenSpan.start(returnVal, startNative);
        var endNative = end;
        dev.gigapixel.rustnlp.ntv.TokenSpan.end(returnVal, endNative);
        

        return returnVal;

    }
    
}

