package in.moneypurse.entity;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

public record CategoryCode(String value) {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9_]{3,32}$");
    private static final Pattern NON_ALPHANUMERIC_PATTERN = Pattern.compile("[^A-Z0-9_]");

    public CategoryCode(String value) {
        Objects.requireNonNull(value, "Category code cannot be null");
        String formatted = value.trim().toUpperCase();
        
        if (!CODE_PATTERN.matcher(formatted).matches()) {
            throw new IllegalArgumentException(
                    "Invalid category code format: '%s'. Must be 3-32 uppercase alphanumeric characters or underscores."
                            .formatted(value));
        }
        this.value = formatted;
    }

    public static CategoryCode of(String value) {
        return new CategoryCode(value);
    }

    public static CategoryCode fromName(String name) {
        Objects.requireNonNull(name, "Category name cannot be null");

        String normalized = Normalizer.normalize(name.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        String sanitized = normalized.toUpperCase().replaceAll("[\\s/\\-]+", "_");
        sanitized = NON_ALPHANUMERIC_PATTERN.matcher(sanitized).replaceAll("");
        sanitized = sanitized.replaceAll("_+", "_");
        sanitized = sanitized.replaceAll("^_+|_+$", "");

        if (sanitized.isBlank()) {
            sanitized = "CAT";
        } else if (sanitized.length() < 3) {
            sanitized = (sanitized + "_CAT").substring(0, Math.min(32, sanitized.length() + 4));
        } else if (sanitized.length() > 32) {
            sanitized = sanitized.substring(0, 32).replaceAll("_+$", "");
        }

        return new CategoryCode(sanitized);
    }
}