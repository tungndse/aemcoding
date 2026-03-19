package com.tungnd.aem.core.models;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.text.SimpleDateFormat;
import java.util.*;

@Model(
        adaptables = Resource.class,
        resourceType = "aemcoding/components/assignment01/articleinfo",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ArticleInfoModel {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String author;

    @ValueMapValue
    private String summary;

    @ValueMapValue
    private Integer readingTime;

    @ValueMapValue
    private String category;

    private static final List<String> CATEGORIES = List.of("Horror", "Math", "History");

    @ValueMapValue
    private Boolean featured;

    @ValueMapValue
    private Date publishedDate;

    @ValueMapValue(name = "cq:tags")
    private String[] tagIds;

    @SlingObject
    private ResourceResolver resourceResolver;

    public boolean isComplete() {
        return StringUtils.isNotBlank(title)
                && StringUtils.isNotBlank(author)
                && StringUtils.isNotBlank(summary);
    }

    public boolean hasReadingTime() {
        return readingTime != null && readingTime > 0;
    }

    public boolean isFeatured() {
        return featured != null && featured;
    }

    public String getFormattedReadingTime() {
        return hasReadingTime() ? readingTime + " min read" : "";
    }

    public String getPublishDate() {
        if (publishedDate == null) {
            return "";
        }
        return new SimpleDateFormat("dd-MM-yyyy").format(publishedDate);
    }

    public boolean hasTags() {
        return !getTagTitles().isEmpty();
    }

    public Set<String> getTagTitles() {
        if (tagIds == null || tagIds.length == 0) {
            return Collections.emptySet();
        }

        if (resourceResolver == null) {
            return Collections.emptySet();
        }

        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        if (tagManager == null) {
            return Collections.emptySet();
        }

        Set<String> titles = new LinkedHashSet<>();
        for (String tagId : tagIds) {
            Tag tag = tagManager.resolve(tagId);
            if (tag != null) {
                titles.add(tag.getTitle());
            }
        }
        return titles;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getCategories() {
        return CATEGORIES;
    }
}