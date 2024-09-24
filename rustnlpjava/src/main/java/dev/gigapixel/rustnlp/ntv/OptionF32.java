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
 * struct OptionF32 {
 *     union {
 *         float ok;
 *     };
 *     bool is_ok;
 * }
 * }
 */
public class OptionF32 {

    OptionF32() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.unionLayout(
            rustnlp_h.C_FLOAT.withName("ok")
        ).withName("$anon$63:1"),
        rustnlp_h.C_BOOL.withName("is_ok"),
        MemoryLayout.paddingLayout(3)
    ).withName("OptionF32");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfFloat ok$LAYOUT = (OfFloat)$LAYOUT.select(groupElement("$anon$63:1"), groupElement("ok"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * float ok
     * }
     */
    public static final OfFloat ok$layout() {
        return ok$LAYOUT;
    }

    private static final long ok$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * float ok
     * }
     */
    public static final long ok$offset() {
        return ok$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * float ok
     * }
     */
    public static float ok(MemorySegment struct) {
        return struct.get(ok$LAYOUT, ok$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * float ok
     * }
     */
    public static void ok(MemorySegment struct, float fieldValue) {
        struct.set(ok$LAYOUT, ok$OFFSET, fieldValue);
    }

    private static final OfBoolean is_ok$LAYOUT = (OfBoolean)$LAYOUT.select(groupElement("is_ok"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * bool is_ok
     * }
     */
    public static final OfBoolean is_ok$layout() {
        return is_ok$LAYOUT;
    }

    private static final long is_ok$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * bool is_ok
     * }
     */
    public static final long is_ok$offset() {
        return is_ok$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * bool is_ok
     * }
     */
    public static boolean is_ok(MemorySegment struct) {
        return struct.get(is_ok$LAYOUT, is_ok$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * bool is_ok
     * }
     */
    public static void is_ok(MemorySegment struct, boolean fieldValue) {
        struct.set(is_ok$LAYOUT, is_ok$OFFSET, fieldValue);
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

