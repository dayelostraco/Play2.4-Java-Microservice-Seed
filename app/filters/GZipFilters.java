package filters;

import play.api.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;
import play.http.HttpFilters;

import javax.inject.Inject;

public class GZipFilters implements HttpFilters {

    @Inject
    private GzipFilter gzipFilter;

    public EssentialFilter[] filters() {
        return new EssentialFilter[]{gzipFilter};
    }
}
