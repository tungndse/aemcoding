package com.tungnd.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SlingResolverDemoModel {

    private static final int MAX_CHILDREN = 8;

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String targetPath;

    private String currentResourcePath;
    private String currentResourceType;
    private String resolverUserId;
    private String containingPagePath;
    private String containingPageTitle;
    private String getResourceResultPath;
    private String getResourceResultType;
    private String resolveResultPath;
    private String resolveResultType;
    private String mappedTargetPath;
    private List<ResolvedChild> targetChildren;

    @PostConstruct
    protected void init() {
        currentResourcePath = currentResource.getPath();
        currentResourceType = currentResource.getResourceType();
        resolverUserId = resourceResolver.getUserID();
        targetChildren = Collections.emptyList();

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager != null) {
            Page containingPage = pageManager.getContainingPage(currentResource);
            if (containingPage != null) {
                containingPagePath = containingPage.getPath();
                containingPageTitle = containingPage.getTitle();
            }
        }

        if (isBlank(targetPath)) {
            return;
        }

        Resource targetResource = resourceResolver.getResource(targetPath);
        if (targetResource != null) {
            getResourceResultPath = targetResource.getPath();
            getResourceResultType = targetResource.getResourceType();
            targetChildren = collectChildren(targetResource);
        }

        Resource resolvedResource = resourceResolver.resolve(targetPath);
        resolveResultPath = resolvedResource.getPath();
        resolveResultType = resolvedResource instanceof NonExistingResource
            ? "non-existing-resource"
            : resolvedResource.getResourceType();

        mappedTargetPath = resourceResolver.map(targetPath);
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getCurrentResourcePath() {
        return currentResourcePath;
    }

    public String getCurrentResourceType() {
        return currentResourceType;
    }

    public String getResolverUserId() {
        return resolverUserId;
    }

    public String getContainingPagePath() {
        return defaultString(containingPagePath);
    }

    public String getContainingPageTitle() {
        return defaultString(containingPageTitle);
    }

    public String getGetResourceResultPath() {
        return defaultString(getResourceResultPath);
    }

    public String getGetResourceResultType() {
        return defaultString(getResourceResultType);
    }

    public String getResolveResultPath() {
        return defaultString(resolveResultPath);
    }

    public String getResolveResultType() {
        return defaultString(resolveResultType);
    }

    public String getMappedTargetPath() {
        return defaultString(mappedTargetPath);
    }

    public List<ResolvedChild> getTargetChildren() {
        return targetChildren;
    }

    public boolean hasTargetPath() {
        return !isBlank(targetPath);
    }

    public boolean hasTargetResource() {
        return !isBlank(getResourceResultPath);
    }

    private List<ResolvedChild> collectChildren(Resource parent) {
        List<ResolvedChild> children = new ArrayList<>();
        Iterator<Resource> iterator = parent.listChildren();
        while (iterator.hasNext() && children.size() < MAX_CHILDREN) {
            Resource child = iterator.next();
            children.add(new ResolvedChild(
                child.getName(),
                child.getPath(),
                child.getResourceType()
            ));
        }
        return children;
    }

    private String defaultString(String value) {
        return isBlank(value) ? "-" : value;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static class ResolvedChild {
        private final String name;
        private final String path;
        private final String resourceType;

        public ResolvedChild(String name, String path, String resourceType) {
            this.name = name;
            this.path = path;
            this.resourceType = resourceType;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public String getResourceType() {
            return resourceType;
        }
    }
}
