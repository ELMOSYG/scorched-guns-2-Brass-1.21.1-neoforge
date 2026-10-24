# Scorched Guns Brass — 1.21.1 NeoForge

Scorched Guns 的黄铜附属，移植到 **Minecraft 1.21.1 / NeoForge**。

由原作者 `ribs` 的 1.20.1 版本（`Scorched Brass 0.0.3`）移植而来，内容保持一致：
黄铜锭、黄铜混合物、锌锭、锌粒、粗锌，黄铜块、锌块、粗锌块、锌矿石、深层锌矿石，以及锌矿的世界生成。

## 什么时候该装它

**只有当整合包里没有任何其它模组提供黄铜或锌时才需要这个附属。**

```
需要：纯 Scorched Guns 整合包（没有 Create / IE / 热力 / 机械动力附属等）
不要装：装了 Create、Create Crafts & Additions、Immersive Engineering 等任一 —— 会出现两套黄铜/锌
```

原版附属没有“检测到其它模组就自动禁用”的能力，1.21.1 上同样没有：物品注册可以在代码里跳过，但配方、战利品表
和锌矿的世界生成是数据文件，**无法按条件加载**。所以这必须是整合包作者的决定。

## 与 1.20.1 原版的差异

| 项 | 改动 |
|---|---|
| 构建 | Forge 1.20.1 → NeoForge 21.1 / ModDevGradle |
| 注册 | 三个类合并为一个（跨类静态初始化曾导致 `ExceptionInInitializerError`） |
| 通用标签 | `forge:tags/{items,blocks}` → `c:tags/{item,block}`，配方里的引用同步改掉 |
| 方块/战利品 | `tags/blocks` / `loot_tables` → `tags/block` / `loot_table`（1.21 单数目录） |
| 世界生成 | `forge/biome_modifier` → `neoforge/biome_modifier`，`forge:add_features` → `neoforge:add_features` |
| 配方结果 | `"result": "x"` / `{"item": "x"}` → `{"id": "x", "count": n}` |
| 资源包 | 补 `pack.mcmeta`（缺它整个资源包不会被识别） |
| 贴图 | 原素材是**二进制**导入的；早期版本误按文本解码，导致 PNG 全部损坏 |
| 汉化 | 新增 `zh_cn.json`（原版只有英文） |

## 构建

```
JAVA_HOME=<jdk21> ./gradlew build
# 产物：build/libs/scguns-brass-1.0.0.jar
```

`build` 之后会自动把 jar 复制到开发实例的 mods 目录（见 `build.gradle` 的 `deployToInstance`）。
只想产出不部署用 `./gradlew jar`。

## 版权

物品、方块与贴图来自原作者 `ribs` 的 Scorched Brass 0.0.3（1.20.1）。本仓库只做版本移植，未改动美术资源。
