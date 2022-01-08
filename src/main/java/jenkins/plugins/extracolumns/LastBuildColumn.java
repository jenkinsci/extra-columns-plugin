/*
 * The MIT License
 *
 * Copyright (c) 2019, Frederic Gurr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jenkins.plugins.extracolumns;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.Util;
import hudson.model.Job;
import hudson.model.Run;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

public class LastBuildColumn extends ListViewColumn {

    private int sortType = 0;
    private int buildType = 0;

    private boolean useRelative = false;
    private boolean showLink = false;
    private boolean showVersion = false;

    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String VERSION_PATTERN = "\\[{2}(ver(sion)?.+?)\\]{2}";

    @DataBoundConstructor
    public LastBuildColumn(int sortType, int buildType, boolean useRelative, boolean showLink, boolean showVersion) {
        super();
        this.sortType = sortType;
        this.buildType = buildType;
        this.useRelative = useRelative;
        this.showLink = showLink;
        this.showVersion = showVersion;
    }

    public LastBuildColumn() {
        this(0, 0, false, false, false);
    }

    public int getSortType(){
        return sortType;
    }

    public int getBuildType(){
        return buildType;
    }

    public boolean getUseRelative() {
        return useRelative;
    }

    public boolean getShowLink() {
        return showLink;
    }

    public boolean getShowVersion() {
        return showVersion;
    }


    public Run<?, ?> getBuild(Job <?, ?> job) {
        switch (getBuildType()) {
        case 0:
            return job.getLastBuild();
        case 1:
            return job.getLastCompletedBuild();
        case 2:
            return job.getLastFailedBuild();
        case 3:
            return job.getLastSuccessfulBuild();
        case 4:
            return job.getLastUnsuccessfulBuild();
        case 5:
            return job.getLastStableBuild();
        case 6:
            return job.getLastUnstableBuild();
        default:
            return job.getLastBuild();
        }
    }

    public String getBuildTypeString() {
        switch (getBuildType()) {
        case 0:
            return Messages.LastBuildColumn_LastBuild();
        case 1:
            return Messages.LastBuildColumn_LastCompletedBuild();
        case 2:
            return Messages.LastBuildColumn_LastFailedBuild();
        case 3:
            return Messages.LastBuildColumn_LastSuccessfulBuild();
        case 4:
            return Messages.LastBuildColumn_LastUnsuccessfulBuild();
        case 5:
            return Messages.LastBuildColumn_LastStableBuild();
        case 6:
            return Messages.LastBuildColumn_LastUnstableBuild();
        default:
            return Messages.LastBuildColumn_LastBuild();
        }
    }

    public String getBuildTypeUrl() {
        switch (getBuildType()) {
        case 0:
            return "lastBuild";
        case 1:
            return "lastCompletedBuild";
        case 2:
            return "lastFailedBuild";
        case 3:
            return "lastSuccessfulBuild";
        case 4:
            return "lastUnsuccessfulBuild";
        case 5:
            return "lastStableBuild";
        case 6:
            return "lastUnstableBuild";
        default:
            return "lastBuild";
        }
    }

    public String getLastBuildStartAbsoluteString(Run<?, ?> build){
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        return sdf.format(build.getStartTimeInMillis());
    }

    public long getBuildEndTimeInMillis(Run<?, ?> build) {
        return build.getStartTimeInMillis() + build.getDuration();
    }

    public String getLastBuildEndAbsoluteString(Run<?, ?> build){
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        return sdf.format(getBuildEndTimeInMillis(build));
    }

    public String getLastBuildEndRelativeString(Run<?, ?> build){
        long duration = new GregorianCalendar().getTimeInMillis()-getBuildEndTimeInMillis(build);
        return Util.getPastTimeString(duration);
    }

    public String getVersionInfoString(Run<?, ?> build){
        final String description = build.getDescription();
        if (description != null) {
            Pattern contentPattern = Pattern.compile(VERSION_PATTERN);
            Matcher contentMatches = contentPattern.matcher(description);
            if (contentMatches.find()) {
                return contentMatches.group(1).trim();
            }
        }
        return null;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {
        
        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.LastBuildColumn_DisplayName();
        }

    }

}
