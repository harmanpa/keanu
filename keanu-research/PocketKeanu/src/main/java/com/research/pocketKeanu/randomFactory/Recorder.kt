package com.research.pocketKeanu.randomFactory

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.abstractTypes.IntLike

class Recorder<D : DoubleLike<D>,I : IntLike<I>>(val factory : RandomFactory<D, I>, val jointP : D) : RandomFactory<D,I> {

    val doubles = ArrayList<D>()
    val ints = ArrayList<I>()
    var isRecording = true
    var doubleCounter = 0;
    var intCounter = 0;

    constructor(factory: RandomFactory<D, I>) : this(factory, factory.nextConstant(0.0)) {
    }

    fun record() {
        doubles.clear()
        ints.clear()
        isRecording = true
    }

    fun replay() {
        doubleCounter = 0
        intCounter = 0
        isRecording = false
    }

    override fun nextGaussian(mu: D, sigma: D): D {
        if(!isRecording) replayNextDoubleLike()
        val r = factory.nextGaussian(mu,sigma)
        doubles.add(r)
        return r
    }

    override fun nextGaussian(mu: Double, sigma: Double): D {
        if(!isRecording) replayNextDoubleLike()
        val r = factory.nextGaussian(mu,sigma)
        doubles.add(r)
        return r
    }

    override fun nextConstant(value: Double): D {
        if(!isRecording) replayNextDoubleLike()
        val r = factory.nextConstant(value)
        doubles.add(r)
        return r
    }

    override fun nextInt(): I {
        if(!isRecording) return replayNextIntLike()
        val r = factory.nextInt();
        ints.add(r)
        return r
    }

    fun replayNextDoubleLike() : D {
        if(intCounter >= ints.size) throw(IndexOutOfBoundsException())
        return doubles[intCounter++]
    }

    fun replayNextIntLike() : I {
        if(intCounter >= ints.size) throw(IndexOutOfBoundsException())
        return ints[intCounter++]
    }
}