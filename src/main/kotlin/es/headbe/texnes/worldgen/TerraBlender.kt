package es.headbe.texnes.worldgen

import es.headbe.texnes.Texnes
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi
import terrablender.worldgen.TBSurfaceRuleData

class TerraBlender : TerraBlenderApi {
    override fun onTerraBlenderInitialized() {
        Regions.register(Biomes())

        SurfaceRuleManager.addToDefaultSurfaceRulesAtStage(SurfaceRuleManager.RuleCategory.OVERWORLD, SurfaceRuleManager.RuleStage.AFTER_BEDROCK, 1, EvaporiteSurfaceRules.makeRules())
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Texnes.MOD_ID, TBSurfaceRuleData.overworld())
    }
}