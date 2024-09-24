package dev.gigapixel.rustnlp

import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure

internal interface TokenSpanLib: Library {
}

internal class TokenSpanNative: Structure(), Structure.ByValue {
    @JvmField
    internal var start: Long = 0;
    @JvmField
    internal var end: Long = 0;
  
    // Define the fields of the struct
    override fun getFieldOrder(): List<String> {
        return listOf("start", "end")
    }
}

class TokenSpan internal constructor (
    internal val nativeStruct: TokenSpanNative) {
    val start: ULong = nativeStruct.start.toULong()
    val end: ULong = nativeStruct.end.toULong()

    companion object {
        internal val libClass: Class<TokenSpanLib> = TokenSpanLib::class.java
        internal val lib: TokenSpanLib = Native.load("rustnlp", libClass)
        val NATIVESIZE: Long = Native.getNativeSize(TokenSpanNative::class.java).toLong()
    }

}
