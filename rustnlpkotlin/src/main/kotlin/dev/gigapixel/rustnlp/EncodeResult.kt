package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface EncodeResultLib: Library {
    fun EncodeResult_destroy(handle: Pointer)
    fun EncodeResult_get(handle: Pointer, i: Int): OptionTokenSpanNative
    fun EncodeResult_get_by_idx(handle: Pointer, i: Int): TokenSpanNative
    fun EncodeResult_len(handle: Pointer): Int
    fun EncodeResult_is_empty(handle: Pointer): Byte
    fun EncodeResult_pair_seq(handle: Pointer): Pointer
}

class EncodeResult internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class EncodeResultCleaner(val handle: Pointer, val lib: EncodeResultLib) : Runnable {
        override fun run() {
            lib.EncodeResult_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<EncodeResultLib> = EncodeResultLib::class.java
        internal val lib: EncodeResultLib = Native.load("rustnlp", libClass)
    }
    
    internal fun getInternal(i: Int): TokenSpan? {
        
        val returnVal = lib.EncodeResult_get(handle, i);
        
        val intermediateOption = returnVal.option() ?: return null
        
        val returnStruct = TokenSpan(intermediateOption)
        return returnStruct
                                
    }
    
    fun getByIdx(i: Int): TokenSpan {
        
        val returnVal = lib.EncodeResult_get_by_idx(handle, i);
        
        val returnStruct = TokenSpan(returnVal)
        return returnStruct
    }
    
    fun len(): Int {
        
        val returnVal = lib.EncodeResult_len(handle);
        return (returnVal)
    }
    
    fun isEmpty(): Boolean {
        
        val returnVal = lib.EncodeResult_is_empty(handle);
        return (returnVal > 0)
    }
    
    fun pairSeq(): PairSeq {
        
        val returnVal = lib.EncodeResult_pair_seq(handle);
        val selfEdges: List<Any> = listOf()
        val handle = returnVal 
        val returnOpaque = PairSeq(handle, selfEdges)
        CLEANER.register(returnOpaque, PairSeq.PairSeqCleaner(handle, PairSeq.lib));
        return returnOpaque
    }

    operator fun get(index: Int): TokenSpan {
        val returnVal = getInternal(index)
        if (returnVal == null) {
            throw IndexOutOfBoundsException("Index $index is out of bounds.")
        } else {
            return returnVal
        }
    }

}
