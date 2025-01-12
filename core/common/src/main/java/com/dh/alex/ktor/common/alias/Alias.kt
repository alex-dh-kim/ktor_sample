package com.dh.alex.ktor.common.alias

import arrow.core.Either
import com.dh.alex.ktor.common.exceptions.DHException

typealias DHResult<R> = Either<DHException, R>
