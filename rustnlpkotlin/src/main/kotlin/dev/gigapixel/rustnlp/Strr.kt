package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface StrrLib: Library {
    fun Strr_destroy(handle: Pointer)
}

class Strr internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class StrrCleaner(val handle: Pointer, val lib: StrrLib) : Runnable {
        override fun run() {
            lib.Strr_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<StrrLib> = StrrLib::class.java
        internal val lib: StrrLib = Native.load("rustnlp", libClass)
    }

}
