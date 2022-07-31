/*
 * SonarSource Language Recognizer Copyright (C) 2010-2017 SonarSource SA mailto:info AT sonarsource
 * DOT com
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package com.petrotec.documentms.common;


import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Loosely modeled after {@link java.util.Optional}.
 *
 * @since 1.21
 */
public abstract class OptionalResponse<T> {

    public static <T> OptionalResponse<T> error(Exception error) {
        return error(Arrays.asList(error));
    }

    public static <T> OptionalResponse<T> error(List<Exception> errorsList) {
        return new Absent<>(Objects.requireNonNull(errorsList));
    }

    public static <T> OptionalResponse<T> of(T reference) {
        return new Present<>(Objects.requireNonNull(reference));
    }

    public static <T> OptionalResponse<T> empty() {
        return new EmptyOptional<>();
    }

    public abstract boolean isPresent();

    public abstract boolean isError();

    public abstract List<Exception> getErrors();

    public abstract T get();

    public abstract T or(T defaultValue);

    @Nullable
    public abstract T orNull();

    private static class EmptyOptional<T> extends OptionalResponse<T> {
        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public List<Exception> getErrors() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public T get() {
            throw new IllegalStateException("value is absent");
        }

        @Override
        public Object or(Object defaultValue) {
            Objects.requireNonNull(defaultValue, "use orNull() instead of or(null)");
            return defaultValue;
        }

        @Nullable
        @Override
        public T orNull() {
            return null;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return object == this;
        }

        @Override
        public int hashCode() {
            return 0x598df91c;
        }

        @Override
        public String toString() {
            return "Optional.empty()";
        }
    }

    private static class Present<T> extends OptionalResponse<T> {
        private final T reference;

        public Present(T reference) {
            this.reference = reference;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public List<Exception> getErrors() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public T get() {
            return reference;
        }

        @Override
        public T or(Object defaultValue) {
            Objects.requireNonNull(defaultValue, "use orNull() instead of or(null)");
            return reference;
        }

        @Nullable
        @Override
        public T orNull() {
            return reference;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof Present) {
                Present other = (Present) object;
                return reference.equals(other.reference);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 0x598df91c + reference.hashCode();
        }

        @Override
        public String toString() {
            return "Optional.of(" + reference + ")";
        }
    }

    private static class Absent<T> extends OptionalResponse<T> {
        private List<Exception> errorList;

        public Absent(List<Exception> errorList) {
            this.errorList = errorList;
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public List<Exception> getErrors() {
            return this.errorList;
        }

        @Override
        public T get() {
            throw new IllegalStateException("value is absent");
        }

        @Override
        public Object or(Object defaultValue) {
            Objects.requireNonNull(defaultValue, "use orNull() instead of or(null)");
            return defaultValue;
        }

        @Nullable
        @Override
        public T orNull() {
            return null;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return object == this;
        }

        @Override
        public int hashCode() {
            return 0x598df91c;
        }

        @Override
        public String toString() {
            return "Optional.absent()";
        }
    }

}
