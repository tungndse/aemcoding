package com.tungnd.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL

)
public class LabBannerModel {
    //TODO : Study the tricks in side this model then see if you can memorize some
    private static final String THEME_DEFAULT = "default";
    private static final String THEME_DARK = "dark";
    private static final String THEME_ACCENT = "accent";
    private static final String ALIGNMENT_LEFT = "left";
    private static final String ALIGNMENT_CENTER = "center";
    private static final String ALIGNMENT_RIGHT = "right";

    @ValueMapValue(name = "label")
    private String label;

    @ValueMapValue(name = "title")
    private String title;

    @ValueMapValue(name = "description")
    private String description;

    @ValueMapValue(name = "ctaText")
    private String ctaText;

    @ValueMapValue(name = "ctaLink")
    private String ctaLink;

    @ValueMapValue(name = "theme")
    private String theme;

    @ValueMapValue(name = "backgroundImage")
    private String backgroundImage;

    @ValueMapValue(name = "openInNewTab")
    private Boolean openInNewTab;

    @ValueMapValue(name = "showOverlay")
    private Boolean showOverlay;

    @ValueMapValue(name = "alignment")
    private String alignment;

    @ValueMapValue(name = "badges")
    private String[] badges;

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getCtaLink() {
        return ctaLink;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public boolean isOpenInNewTab() {
        return Boolean.TRUE.equals(openInNewTab);
    }

    public boolean isShowOverlay() {
        return Boolean.TRUE.equals(showOverlay) && hasBackgroundImage();
    }

    public String getThemeClass() {
        return "labbanner--" + normalizeTheme(theme);
    }

    public String getAlignment() {
        return normalizeAlignment(alignment);
    }

    public List<String> getBadges() {
        if (badges == null || badges.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.stream(badges)
                .filter(value -> value != null && !value.trim().isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isReady() {
        return hasText(title) || hasText(description) || hasText(label);
    }

    public boolean hasBackgroundImage() {
        return hasText(backgroundImage);
    }

    private static String normalizeTheme(String value) {
        String normalized = normalize(value);
        if (THEME_DARK.equals(normalized) || THEME_ACCENT.equals(normalized)) {
            return normalized;
        }
        return THEME_DEFAULT;
    }

    private static String normalizeAlignment(String value) {
        String normalized = normalize(value);
        if (ALIGNMENT_CENTER.equals(normalized) || ALIGNMENT_RIGHT.equals(normalized)) {
            return normalized;
        }
        return ALIGNMENT_LEFT;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
