package com.btvn.projectfinal.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

public class ValidationGroups {
    public interface NotEmptyGroup {}
    public interface FormatGroup {}
    public interface SizeGroup {}

    @GroupSequence({NotEmptyGroup.class, SizeGroup.class, FormatGroup.class, Default.class})
    public interface OrderedChecks {}
}
