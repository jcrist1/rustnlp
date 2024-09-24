package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface TokResLib: Library {
    fun TokRes_destroy(handle: Pointer)
    fun TokRes_get(handle: Pointer, i: Long): OptionSlice
}

class TokRes internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class TokResCleaner(val handle: Pointer, val lib: TokResLib) : Runnable {
        override fun run() {
            lib.TokRes_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<TokResLib> = TokResLib::class.java
        internal val lib: TokResLib = Native.load("rustnlp", libClass)
    }
    
    internal fun getInternal(i: ULong): String? {
        
        val returnVal = lib.TokRes_get(handle, i.toLong());
        
        val intermediateOption = returnVal.option() ?: return null
            return PrimitiveArrayTools.getUtf8(intermediateOption)
                                
    }

    operator fun get(index: ULong): String {
        val returnVal = getInternal(index)
        if (returnVal == null) {
            throw IndexOutOfBoundsException("Index $index is out of bounds.")
        } else {
            return returnVal
        }
    }

}
