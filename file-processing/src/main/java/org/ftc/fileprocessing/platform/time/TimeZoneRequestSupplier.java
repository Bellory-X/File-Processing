package org.ftc.fileprocessing.platform.time;

import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Supplier;

public interface TimeZoneRequestSupplier extends Supplier<Optional<TimeZone>> {
}
