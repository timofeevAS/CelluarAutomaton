import automat.Automat
import org.knowm.xchart.*
import org.knowm.xchart.style.Styler
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.Color
import java.awt.Dimension
import javax.swing.SwingUtilities

class AutomatVisualizer(private val automat: Automat, steps: Int, delay: Long) {
    private val chart = XYChartBuilder().theme(Styler.ChartTheme.GGPlot2).build().also {
        with(it.styler) {
            chartBackgroundColor = Color.GRAY
            legendBackgroundColor = Color.GRAY
            chartFontColor = Color.BLACK
            isPlotGridLinesVisible = false
            isAxisTicksMarksVisible = false
            isXAxisTicksVisible = false
            isYAxisTicksVisible = false
            plotBackgroundColor = Color.BLACK
            defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
            seriesColors = arrayOf(Color(99,174,247),Color(121,2,26))
            seriesMarkers = arrayOf(SeriesMarkers.SQUARE)

        }
    }

    init {
        addSeries()
        updateChart("Seed")

        val gui = SwingWrapper(chart)

        gui.displayChart()

        chart.getStyler().setMarkerSize(20)
        var generation = 0
        val trueHeight = (800*(automat.height/30.0)).toInt()
        val trueWidth = (750*(automat.width)/30.0).toInt()


        while (true) {
            gui.xChartPanel.topLevelAncestor.setSize(trueHeight,trueWidth)

            if (steps != -1 && generation == steps) break
            automat.applyRule()
            Thread.sleep(delay)
            SwingUtilities.invokeLater {
                updateChart("Generation ${++generation}")
                gui.repaintChart()
            }
        }
    }

    private fun updateSeries(state: Array<Array<Boolean>>) {
        val xData = mutableListOf<Double>()
        val yData = mutableListOf<Double>()

        val xDataFalses = mutableListOf<Double>()
        val yDataFalses = mutableListOf<Double>()

        for (i in state.indices) {
            for (j in state[i].indices) {
                if (state[i][j]) {
                    xData.add(j.toDouble())
                    yData.add(i.toDouble())
                }
                else {
                    xDataFalses.add(j.toDouble())
                    yDataFalses.add(i.toDouble())
                }

            }
        }
        chart.updateXYSeries("Alive Cells", xData, yData, null)
        chart.updateXYSeries("Dead Cells", xDataFalses, yDataFalses, null)
    }

    private fun updateChart(title: String) {
        chart.title = title
        val currentState = automat.currentState()
        updateSeries(currentState)
    }

    private fun addSeries() {
        chart.addSeries("Alive Cells", mutableListOf(0.0), mutableListOf(0.0))
        chart.addSeries("Dead Cells", mutableListOf(0.0), mutableListOf(0.0))
    }
}
