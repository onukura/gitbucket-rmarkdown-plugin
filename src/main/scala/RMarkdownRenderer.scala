import gitbucket.core.controller.Context
import gitbucket.core.plugin.{RenderRequest, Renderer}
import gitbucket.core.service.RepositoryService.RepositoryInfo
import gitbucket.core.view.helpers.markdown
import play.twirl.api.Html

class RMarkdownRenderer extends Renderer {

  def render(request: RenderRequest): Html = {
    import request._
    Html(toHtml(filePath, fileContent, branch, repository, enableWikiLink, enableRefsLink, enableAnchor)(context))
  }

  def toHtml(
              filePath: List[String],
              fileContent: String,
              branch: String,
              repository: RepositoryInfo,
              enableWikiLink: Boolean,
              enableRefsLink: Boolean,
              enableAnchor: Boolean)(implicit context: Context): String = {

    val content = fileContent
      .replaceAll("^---\\s*\ntitle:(.*)","# $1\n\n```")
      .replaceAll("\n---\\s*\n", "\n```\n")

    val rendered = markdown(content, repository, branch, enableWikiLink, enableRefsLink, enableLineBreaks = true)

    val delim = "$"

    s"""<script>
       MathJax = {
         tex: {
           inlineMath: [['$delim', '$delim'], ['\\(', '\\)']]
         }
       };
       </script>
       <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
       <script type="text/javascript" id="MathJax-script" async
         src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js">
       </script>$rendered"""

  }

  def shutdown(): Unit = {
  }

}
