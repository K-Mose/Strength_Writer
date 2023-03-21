package com.example.strengthwriter.data.local

import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.utils.Exercise
import com.example.strengthwriter.utils.Unit

object TestData {
    val missions: List<DailyMission> =
        listOf(
            DailyMission(
                id = 1,
                title = "Day 1",
                date = "20230320",
                workout = listOf(
                    Workout(
                        id = 1,
                        name = Exercise.SHOULDER_PRESS,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                        )
                    ),

                    Workout(
                        id = 2,
                        name = Exercise.DEAD_LIFT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 4,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    ),
                    Workout(
                        id = 2,
                        name = Exercise.BACK_SQUAT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 4,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    )
                )
            ),
            DailyMission(
                id = 2,
                title = "Day 2",
                date = "20230321",
                workout = listOf(
                    Workout(
                        id = 3,
                        name = Exercise.SHOULDER_PRESS,
                        date = "20230321",
                        sets = listOf(
                            Sets(
                                id = 7,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 8,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 9,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            )
                        )
                    ),

                    Workout(
                        id = 4,
                        name = Exercise.DEAD_LIFT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 10,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 11,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 12,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    )
                )
            ),
            DailyMission(
                id = 3,
                title = "Day 3",
                date = "20230320",
                workout = listOf(
                    Workout(
                        id = 1,
                        name = Exercise.SHOULDER_PRESS,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                        )
                    ),

                    Workout(
                        id = 2,
                        name = Exercise.DEAD_LIFT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 4,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    ),
                    Workout(
                        id = 2,
                        name = Exercise.BACK_SQUAT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 4,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    )
                )
            ),
            DailyMission(
                id = 4,
                title = "Day 4",
                date = "20230321",
                workout = listOf(
                    Workout(
                        id = 3,
                        name = Exercise.SHOULDER_PRESS,
                        date = "20230321",
                        sets = listOf(
                            Sets(
                                id = 7,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 8,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 9,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            )
                        )
                    ),

                    Workout(
                        id = 4,
                        name = Exercise.DEAD_LIFT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 10,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 11,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 12,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    )
                )
            ),
            DailyMission(
                id = 5,
                title = "Day 5",
                date = "20230320",
                workout = listOf(
                    Workout(
                        id = 1,
                        name = Exercise.SHOULDER_PRESS,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 1,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 2,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 3,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            ),
                        )
                    ),

                    Workout(
                        id = 2,
                        name = Exercise.DEAD_LIFT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 4,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    ),
                    Workout(
                        id = 2,
                        name = Exercise.BACK_SQUAT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 4,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 5,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 6,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    )
                )
            ),
            DailyMission(
                id = 6,
                title = "Day 6",
                date = "20230321",
                workout = listOf(
                    Workout(
                        id = 3,
                        name = Exercise.SHOULDER_PRESS,
                        date = "20230321",
                        sets = listOf(
                            Sets(
                                id = 7,
                                repetition = 6,
                                weight = 55.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 8,
                                repetition = 5,
                                weight = 75.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 9,
                                repetition = 3,
                                weight = 95.0,
                                unit = Unit.LBS,
                            )
                        )
                    ),

                    Workout(
                        id = 4,
                        name = Exercise.DEAD_LIFT,
                        date = "20230320",
                        sets = listOf(
                            Sets(
                                id = 10,
                                repetition = 10,
                                weight = 255.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 11,
                                repetition = 7,
                                weight = 275.0,
                                unit = Unit.LBS,
                            ),
                            Sets(
                                id = 12,
                                repetition = 3,
                                weight = 295.0,
                                unit = Unit.LBS,
                            )
                        )
                    )
                )
            ),
        )
}