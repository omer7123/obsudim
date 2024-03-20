package com.example.mypsychologist.domain.entity

class CSIResultEntity (
    val problemResolutionStrategy: Pair<Int, Int> = Pair(0,0),
    val strategyForSeekingSocialSupport: Pair<Int, Int> = Pair(0,0),
    val avoidanceStrategy: Pair<Int, Int> = Pair(0,0),
)
