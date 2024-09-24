package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface StemLib: Library {
    fun Stem_destroy(handle: Pointer)
    fun Stem_create(): Pointer
    fun Stem_stem(handle: Pointer, strs: Slice): Pointer
}

class Stem internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class StemCleaner(val handle: Pointer, val lib: StemLib) : Runnable {
        override fun run() {
            lib.Stem_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<StemLib> = StemLib::class.java
        internal val lib: StemLib = Native.load("rustnlp", libClass)
        
        fun create(): Stem {
            
            val returnVal = lib.Stem_create();
            val selfEdges: List<Any> = listOf()
            val handle = returnVal 
            val returnOpaque = Stem(handle, selfEdges)
            CLEANER.register(returnOpaque, Stem.StemCleaner(handle, Stem.lib));
            return returnOpaque
        }
    }
    
    fun stem(strs: Array<String>): Strings {
        val (strsMem, strsSlice) = PrimitiveArrayTools.readUtf8s(strs)
        
        val returnVal = lib.Stem_stem(handle, strsSlice);
        val selfEdges: List<Any> = listOf()
        val handle = returnVal 
        val returnOpaque = Strings(handle, selfEdges)
        CLEANER.register(returnOpaque, Strings.StringsCleaner(handle, Strings.lib));
        strsMem.forEach {it.close()}
        return returnOpaque
    }

}
