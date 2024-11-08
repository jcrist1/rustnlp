// Generated by jextract

package dev.gigapixel.rustnlp.ntv;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct TokenSpan {
 *     size_t start;
 *     size_t end;
 * }
 * }
 */
public class TokenSpan {

    TokenSpan() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        rustnlp_h.C_LONG.withName("start"),
        rustnlp_h.C_LONG.withName("end")
    ).withName("TokenSpan");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfLong start$LAYOUT = (OfLong)$LAYOUT.select(groupElement("start"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * size_t start
     * }
     */
    public static final OfLong start$layout() {
        return start$LAYOUT;
    }

    private static final long start$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * size_t start
     * }
     */
    public static final long start$offset() {
        return start$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * size_t start
     * }
     */
    public static long start(MemorySegment struct) {
        return struct.get(start$LAYOUT, start$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * size_t start
     * }
     */
    public static void start(MemorySegment struct, long fieldValue) {
        struct.set(start$LAYOUT, start$OFFSET, fieldValue);
    }

    private static final OfLong end$LAYOUT = (OfLong)$LAYOUT.select(groupElement("end"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * size_t end
     * }
     */
    public static final OfLong end$layout() {
        return end$LAYOUT;
    }

    private static final long end$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * size_t end
     * }
     */
    public static final long end$offset() {
        return end$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * size_t end
     * }
     */
    public static long end(MemorySegment struct) {
        return struct.get(end$LAYOUT, end$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * size_t end
     * }
     */
    public static void end(MemorySegment struct, long fieldValue) {
        struct.set(end$LAYOUT, end$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}

