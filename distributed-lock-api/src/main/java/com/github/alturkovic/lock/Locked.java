/*
 * Copyright (c)  2017 Alen Turković <alturkovic@gmail.com>
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.alturkovic.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Locked {

  /**
   * Flag to indicate if lock will be manually released.
   * By default, lock will be released after method execution
   */
  boolean manuallyReleased() default false;

  /**
   * Id of a specific store for lock to use.
   * For JDBC, this would be a lock table.
   * For Mongo, this would be a collection name.
   */
  String storeId() default "lock";

  /**
   * Prefix of all generated lock keys
   */
  String prefix() default "lock:";

  /**
   * SpEL expression with all arguments passed as SpEL variables {@code args} and available execution context.
   * By default, it will evaluate to the absolute method path (class + method) by evaluating a special 'executionPath' variable
   */
  String expression() default "#executionPath";

  /**
   * Names of parameters in expression.
   * Parameters will be available to the SpEL as: 'parameter' + index (default: p0, p1, ...)
   */
  String parameter() default "p";

  /**
   * Lock expiration interval
   */
  Interval expiration() default @Interval("10");

  /**
   * Lock timeout interval
   */
  Interval timeout() default @Interval("1");

  /**
   * Lock retry interval
   */
  Interval retry() default @Interval(value = "50", unit = TimeUnit.MILLISECONDS);

  /**
   * Lock type, see implementations of {@link Lock}
   */
  Class<? extends Lock> type() default Lock.class;
}
