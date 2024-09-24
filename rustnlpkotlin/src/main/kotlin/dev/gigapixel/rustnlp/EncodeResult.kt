package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface EncodeResultLib: Library {
    fun EncodeResult_destroy(handle: Pointer)
    fun EncodeResult_get(handle: Pointer, i: Long): OptionTokenSpanNative
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
    
    internal fun getInternal(i: ULong): TokenSpan? {
        
        val returnVal = lib.EncodeResult_get(handle, i.toLong());
        
        val intermediateOption = returnVal.option() ?: return null
        
        val returnStruct = TokenSpan(intermediateOption)
        return returnStruct
                                
    }

    operator fun get(index: ULong): TokenSpan {
        val returnVal = getInternal(index)
        if (returnVal == null) {
            throw IndexOutOfBoundsException("Index $index is out of bounds.")
        } else {
            return returnVal
        }
    }

}
