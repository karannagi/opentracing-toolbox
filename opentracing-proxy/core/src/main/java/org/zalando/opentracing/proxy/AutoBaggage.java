package org.zalando.opentracing.proxy;

import io.opentracing.Span;
import io.opentracing.tag.Tag;
import lombok.AllArgsConstructor;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

// TODO doesn't work for tagging of builders...
@API(status = EXPERIMENTAL)
@AllArgsConstructor
public final class AutoBaggage implements TagListener {

    private final String tagKey;
    private final String baggageKey;

    public AutoBaggage(final String key) {
        this(key, key);
    }

    @Override
    public <T> void onTag(final Span span, final Tag<T> tag, final T value) {
        if (baggageExists(span)) {
            return;
        }

        if (tagKey.equals(tag.getKey())) {
            span.setBaggageItem(baggageKey, String.valueOf(value));
        }
    }

    private boolean baggageExists(final Span span) {
        return span.getBaggageItem(baggageKey) != null;
    }

}
