package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface StringsLib: Library {
    fun Strings_destroy(handle: Pointer)
}

class Strings internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class StringsCleaner(val handle: Pointer, val lib: StringsLib) : Runnable {
        override fun run() {
            lib.Strings_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<StringsLib> = StringsLib::class.java
        internal val lib: StringsLib = Native.load("rustnlp", libClass)
    }

}
