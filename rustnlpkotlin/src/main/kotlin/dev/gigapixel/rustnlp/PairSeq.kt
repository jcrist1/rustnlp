package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface PairSeqLib: Library {
    fun PairSeq_destroy(handle: Pointer)
    fun PairSeq_get_slice(handle: Pointer): Slice
}

class PairSeq internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class PairSeqCleaner(val handle: Pointer, val lib: PairSeqLib) : Runnable {
        override fun run() {
            lib.PairSeq_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<PairSeqLib> = PairSeqLib::class.java
        internal val lib: PairSeqLib = Native.load("rustnlp", libClass)
    }
    
    fun getSlice(): IntArray {
        
        val returnVal = lib.PairSeq_get_slice(handle);
            return PrimitiveArrayTools.getIntArray(returnVal)
    }

}
