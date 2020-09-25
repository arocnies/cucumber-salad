package dev.nies.salad.script.cucumber

import io.cucumber.core.backend.Backend
import io.cucumber.core.backend.BackendProviderService
import io.cucumber.core.backend.Container
import io.cucumber.core.backend.Lookup
import io.cucumber.java8.KtsBackend
import java.util.function.Supplier

class KtsBackendProviderService : BackendProviderService {
    override fun create(lookup: Lookup?, container: Container?, supplier: Supplier<ClassLoader?>?): Backend {
        requireNotNull(lookup)
        requireNotNull(container)
        requireNotNull(supplier)
        return KtsBackend(lookup, container, supplier)
    }
}