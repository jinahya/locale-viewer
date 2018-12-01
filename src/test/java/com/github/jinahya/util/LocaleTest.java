/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jinahya.util;

import org.testng.annotations.Test;

import java.util.Locale;

/**
 * A class for testing {@link Locale}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class LocaleTest {

    @Test
    public void printFirstLocale() {
        final Locale[] locales = Locale.getAvailableLocales();
        System.out.println("first locale: " + locales[0] + " " + locales[0].hashCode());
        System.out.println("first locale == Locale.ROOT: " + (locales[0] == Locale.ROOT));
    }

    @Test(enabled = false)
    public void printAll() {
        final Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            System.out.printf("%03d %s\n", i, locales[i]);
        }
    }
}
