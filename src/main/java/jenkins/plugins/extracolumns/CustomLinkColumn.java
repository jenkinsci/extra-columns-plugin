package jenkins.plugins.extracolumns;

import hudson.Extension;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Adds a column to view, which contains a customizable link.
 *
 */
public class CustomLinkColumn extends ListViewColumn
{
    private String columnHeader;
    private String icon;
    private String title;
    private String url;

    @DataBoundConstructor
    public CustomLinkColumn(final String columnHeader, final String icon, final String title, final String url)
    {
        super();
        this.columnHeader = columnHeader;
        this.icon = icon;
        this.title = title;
        this.url = url;
    }

    public String getColumnHeader()
    {
        return columnHeader;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUrl()
    {
        return url;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor
    {
        @Override
        public boolean shownByDefault()
        {
            return false;
        }

        @Override
        public String getDisplayName()
        {
            return Messages.CustomLinkColumn_DisplayName();
        }

    }
}
