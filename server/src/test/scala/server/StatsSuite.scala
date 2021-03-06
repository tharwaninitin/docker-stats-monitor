package server

import cats.implicits._
import munit.FunSuite

class StatsSuite extends FunSuite {

  test("parse") {
    val line   =
      "ed4e5c72308a-everythingelseistruncated,,,affectionate_shockley,,,0.00%,,,1.148MiB / 1.944GiB,,,0.06%,,,1.45kB / 0B,,,0B / 0B,,,1"
    val result = Stats(
      "ed4e5c72308a",
      "affectionate_shockley",
      0.00,
      "1.148MiB / 1.944GiB",
      0.06,
      "1.45kB / 0B",
      "0B / 0B",
      1
    )
    assertEquals(Stats.parseCSV[Either[Throwable, *]](line), Right(result))
  }

  test("less punishing parsing") {
    val line   =
      "short,,,affectionate_shockley,,,--,,,1.148MiB / 1.944GiB,,,--,,,1.45kB / 0B,,,0B / 0B,,,--"
    val result = Stats(
      "",
      "affectionate_shockley",
      0.0,
      "1.148MiB / 1.944GiB",
      0.0,
      "1.45kB / 0B",
      "0B / 0B",
      0
    )
    assertEquals(Stats.parseCSV[Either[Throwable, *]](line), Right(result))
  }

  test("fail") {
    assert(Stats.parseCSV[Either[Throwable, *]]("").isLeft)
  }
}
