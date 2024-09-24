package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface BoxErrLib: Library {
    fun BoxErr_destroy(handle: Pointer)
    fun BoxErr_get(handle: Pointer): Slice
}

class BoxErr internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class BoxErrCleaner(val handle: Pointer, val lib: BoxErrLib) : Runnable {
        override fun run() {
            lib.BoxErr_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<BoxErrLib> = BoxErrLib::class.java
        internal val lib: BoxErrLib = Native.load("rustnlp", libClass)
    }
    
    fun get(): String {
        
        val returnVal = lib.BoxErr_get(handle);
            return PrimitiveArrayTools.getUtf8(returnVal)
    }

}
