package com.ecg.hudson.plugins.columns;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Job;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.time.FastDateFormat;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * View column that shows the date of last job configuration modification.
 * 
 * @author Stephan Krull, ECG Leipzig GmbH
 * 
 */
public class LastJobConfigurationModificationColumn extends ListViewColumn
{

	private static final Logger	LOGGER	= Logger.getLogger(LastJobConfigurationModificationColumn.class.getName());

    @DataBoundConstructor
    public LastJobConfigurationModificationColumn()
    {}

    public String getInfo(Job<?, ?> job)
	{
		XmlFile config = job.getConfigFile();
		if (config == null || !config.exists()) return "N/A";

		try
		{
			long lm = config.getFile().lastModified();
			return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date(lm));
		}
		catch (Exception e)
		{
            LOGGER.log(Level.WARNING, "Cannot read last modification date of configuration for job '" + job.getName() + "'.",e);
			return "N/A";
		}

	}

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor
	{
		@Override
		public String getDisplayName()
		{
			return Messages.LastJobConfigurationModificationColumn_DisplayName();
		}

		@Override
        public boolean shownByDefault()
		{
            return false;
		}

	}
}
