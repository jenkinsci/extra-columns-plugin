# Extra-Columns plugin for Jenkins

[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/extra-columns.svg)](https://plugins.jenkins.io/extra-columns)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/extra-columns-plugin.svg?label=release)](https://github.com/jenkinsci/extra-columns-plugin/releases/latest)
[![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/extra-columns.svg?color=blue)](https://plugins.jenkins.io/extra-columns)
[![Build Status](https://ci.jenkins.io/buildStatus/icon?job=Plugins%2Fextra-columns-plugin%2Fmaster)](https://ci.jenkins.io/job/Plugins/job/extra-columns-plugin/job/master/)
[![GitHub license](https://img.shields.io/github/license/jenkinsci/extra-columns-plugin.svg)](https://github.com/jenkinsci/extra-columns-plugin/blob/master/LICENSE.txt)
[![Maintenance](https://img.shields.io/maintenance/yes/2024.svg)](https://github.com/jenkinsci/extra-columns-plugin)

## Note
Older versions of this plugin may not be safe to use. Please review the following warnings before using an older version:
* [Stored XSS vulnerability](https://jenkins.io/security/advisory/2016-04-11/)

## Purpose

This is a plugin for the [Jenkins CI server](https://jenkins.io) which provides additional columns in the main UI screen.
It's using the [listview-column extension](https://www.jenkins.io/doc/developer/extensions/jenkins-core/#listviewcolumn).
Since additional columns do not require a lot of code, the intention is to bundle multiple columns in one plugin instead of having a separate plugin for each column.

It currently provides the following columns:

* Build description
  * Shows the build description of the last build
  * Options: Force column width
* Build duration
  * Show last build duration
  * Options: Time since build started, average duration, average duration compact
* Build parameters
  * Shows either all build parameters or build parameters maching a regular expression
  * Options: Use regular expression
* Configure build button
  * Show shortcut to configuration page of a job
* Disable/enable Project button/icon
  * Disable/enable job with one click
  * Options: Use button or icon
* Job type
  * Show job type (eg. Freestyle, Maven, Multi Branch, etc)
  * Options: Use pronoun
* Last build
  * Shows last build
  * Options: Build Start/End, Build Selector, Show relative times, Show link to build
* Last build node
  * Shows last build node, where job was running
* Last/current build console
  * Shows shortcut to last/current build console
* Last project configuration modification
  * Shows date of last configuration modification
* Periodic build trigger
  * Shows last build trigger in cron format
* Project description
  * Shows the job/project description
  * Options: Display job name, Trim description, Force column width
* SCM type
  * Shows the type of SCM that is used in the job (e.g. CVS, SVN, Git)
* Agent or label restriction
  * Shows the agent or label restrictions
* Test result
  * Shows the test results of the last build
  * Options: Test result format
* User name
  * Shows the name of the user that started the last build
* Workspace link
  * Shows a link to the job's workspace

Some columns have been adapted from existing plugins that in some cases are not actively maintained anymore, e.g.

* ~~[Build node plugin](https://plugins.jenkins.io/build-node-column)~~ (SUSPENDED)
* [Configure job column plugin](https://plugins.jenkins.io/configure-job-column-plugin/)
* [Console column plugin](https://plugins.jenkins.io/console-column-plugin/)
* [Description column plugin](https://plugins.jenkins.io/description-column-plugin/)
* [Job type column plugin](https://plugins.jenkins.io/jobtype-column/)

## Changelog & Releases

See [GitHub releases](https://github.com/jenkinsci/extra-columns-plugin/releases)


## Usage


### How to add a column

* Create a new view by clicking on the "+" tab above the list of jobs, select "list view" as type and give it a name. At the bottom of the configuration page of the new view you can add more columns and change their order.
* If you want to add columns to the All view, follow the guide on the [Editing or Replacing the All View wiki page](https://wiki.jenkins-ci.org/display/JENKINS/Editing+or+Replacing+the+All+View).

### How to configure a column

Columns can be configured on the "Edit view" page. Please note, not all columns can be configured.

## Support


[Please create an issue in the Jenkins JIRA on the "extra-columns-plugin" component](https://issues.jenkins-ci.org/secure/CreateIssueDetails!init.jspa?pid=10172&issuetype=1&components=15943).
__Please do not use GitHub issues!__

## Contributions

Since the column API is quite simple, it's easy to get started.
Contributions are more than welcome. :)

### You have an idea for a new column or want to improve an existing column?
The fastest way to get this done, is to fork the GitHub project, take a look at the existing code, copy and/or modify a column and create a pull-request.


