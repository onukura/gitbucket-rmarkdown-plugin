import javax.servlet.ServletContext
import gitbucket.core.plugin.{PluginRegistry}
import gitbucket.core.service.SystemSettingsService
import gitbucket.core.service.SystemSettingsService.SystemSettings
import io.github.gitbucket.solidbase.model.Version

import scala.util.Try

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "rmarkdown"
  override val pluginName: String = "R markdown renderer Plugin"
  override val description: String = "Rendering R markdown files."
  override val versions: List[Version] = List(
    new Version("1.0.0"),
  )

  private[this] var renderer: Option[RMarkdownRenderer] = None

  override def initialize(registry: PluginRegistry, context: ServletContext, settings: SystemSettingsService.SystemSettings): Unit = {
    val test = Try{ new RMarkdownRenderer() }
    val rmarkdown = test.get
    registry.addRenderer("Rmd", rmarkdown)
    registry.addRenderer("rmd", rmarkdown)
    renderer = Option(rmarkdown)
    super.initialize(registry, context, settings)
  }

  override def shutdown(registry: PluginRegistry, context: ServletContext, settings: SystemSettings): Unit = {
    renderer.map(r => r.shutdown())
  }

}