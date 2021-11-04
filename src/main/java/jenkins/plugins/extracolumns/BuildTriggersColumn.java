package jenkins.plugins.extracolumns;

import hudson.Extension;
import hudson.Util;
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.scm.NullSCM;
import hudson.scm.SCM;
import hudson.triggers.SCMTrigger;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;
import jenkins.model.ParameterizedJobMixIn;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.List;
import java.util.Map;

public class BuildTriggersColumn extends ListViewColumn {

    private int columnWidth;
    private boolean forceWidth;

    @DataBoundConstructor
    public BuildTriggersColumn(int columnWidth, boolean forceWidth) {
        this.columnWidth = columnWidth;
        this.forceWidth = forceWidth;
    }

    public BuildTriggersColumn() {
        this(80, false);
    }

    public String getBuildTriggers(@SuppressWarnings("rawtypes") Job job) {
        String upstreamTriggerName = jenkins.triggers.Messages.ReverseBuildTrigger_build_after_other_projects_are_built();

        StringBuilder result = new StringBuilder();

        List<AbstractProject> upstreamProjects = getUpstreamProjects(job);
        if (upstreamProjects != null && !upstreamProjects.isEmpty()) {
            result.append(escape(upstreamTriggerName)).append(": ");
            append(result, upstreamProjects);
        }

        Map<TriggerDescriptor, Trigger<?>> triggers = getTriggers(job);
        if (triggers != null) {
            boolean hasSourceCodeManagement = hasSourceCodeManagement(job);

            for (Map.Entry<TriggerDescriptor, Trigger<?>> trigger : triggers.entrySet()) {
                if (upstreamTriggerName.equals(trigger.getKey().getDisplayName()))
                    continue;

                if (result.length() > 0)
                    result.append("<br>");

                boolean isDisabled = !hasSourceCodeManagement && trigger.getValue() instanceof SCMTrigger;

                if (isDisabled)
                    result.append("<i>[")
                            .append(escape(Messages.BuildTriggersColumn_Disabled()))
                            .append("] ");

                result.append(escape(trigger.getKey().getDisplayName()));

                String spec = trigger.getValue().getSpec();
                if (spec != null && spec.trim().length() > 0)
                    result.append(": ")
                            .append(escape(spec));

                if (isDisabled)
                    result.append("</i>");
            }
        }

        return result.toString();
    }

    private List<AbstractProject> getUpstreamProjects(Job job) {
        if (!(job instanceof AbstractProject))
            return null;

        AbstractProject proj = (AbstractProject) job;

        return proj.getUpstreamProjects();
    }

    private boolean hasSourceCodeManagement(Job job) {
        if (!(job instanceof AbstractProject))
            return false;

        AbstractProject proj = (AbstractProject) job;

        SCM sourceCodeManagement = proj.getScm();
        return sourceCodeManagement != null && !(sourceCodeManagement instanceof NullSCM);
    }

    private Map<TriggerDescriptor, Trigger<?>> getTriggers(Job aJob) {
        if (!(aJob instanceof ParameterizedJobMixIn.ParameterizedJob))
            return null;

        ParameterizedJobMixIn.ParameterizedJob job = (ParameterizedJobMixIn.ParameterizedJob) aJob;

        return job.getTriggers();
    }

    private void append(StringBuilder result, List<AbstractProject> upstreamTriggers) {
        for (int i = 0; i < upstreamTriggers.size(); i++) {
            AbstractProject up = upstreamTriggers.get(i);
            if (i > 0)
                result.append(", ");
            result.append(escape(up.getDisplayName()));
        }
    }

    private String escape(String txt) {
        if (txt == null)
            txt = "";

        return Util.escape(txt.trim()).replace("\n", "<br>");
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {

        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.BuildTriggersColumn_DisplayName();
        }

        @Override
        public String getHelpFile() {
            return "/plugin/extra-columns/help-buildTriggers-column.html";
        }
    }
}
