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

public class Tok {

    MemorySegment internal;
    Cleaner.Cleanable cleanable;
    SegmentAllocator arena;

    List<Object> selfEdges = List.of();
    

    static class TokCleaner implements Runnable {

        MemorySegment segment;
        TokCleaner(MemorySegment segment) {
            this.segment = segment;
        }

        public void run() {
            rustnlp_h.Tok_destroy(this.segment);
        }
    }

    Tok() {}
    Tok(MemorySegment handle, List<Object> selfEdges) {
        this.internal = handle;
        this.selfEdges = selfEdges;
        

    }
    
    public static Tok create() {
        
        var nativeVal = rustnlp_h.Tok_create();
        
        List<Object> selfEdges = List.of();
        
        
        
        var returnVal = new Tok(nativeVal, selfEdges);
        return returnVal;
                
    }
    
    
    public TokRes tokenize(String text) {
        
        try (var arena = Arena.ofConfined()) {
            
            var textData= Arena.ofAuto().allocateFrom(text, StandardCharsets.UTF_8);
            var textLen = textData.byteSize() - 1;  // allocated strings are null terminated
            var textView = DiplomatStringView.allocate(Arena.ofAuto());
            DiplomatStringView.len(textView, textLen);
            DiplomatStringView.data(textView, textData);
            var nativeVal = rustnlp_h.Tok_tokenize(internal, textView);
            
            List<Object> selfEdges = List.of();
            
            
            
            var returnVal = new TokRes(nativeVal, selfEdges);
            return returnVal;
                    
        }
    }
    
    public EncodeResult tokenizeAsOffsets(String text) {
        
        try (var arena = Arena.ofConfined()) {
            
            var textData= Arena.ofAuto().allocateFrom(text, StandardCharsets.UTF_8);
            var textLen = textData.byteSize() - 1;  // allocated strings are null terminated
            var textView = DiplomatStringView.allocate(Arena.ofAuto());
            DiplomatStringView.len(textView, textLen);
            DiplomatStringView.data(textView, textData);
            var nativeVal = rustnlp_h.Tok_tokenize_as_offsets(internal, textView);
            
            List<Object> selfEdges = List.of();
            
            
            
            var returnVal = new EncodeResult(nativeVal, selfEdges);
            return returnVal;
                    
        }
    }
    
}