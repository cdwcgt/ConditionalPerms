# ConditionalPerms [![Build Status](https://ci.lucko.me/job/ConditionalPerms/badge/icon)](https://ci.lucko.me/job/ConditionalPerms/)
Define permissions that only apply when conditions are met

## Links
* **Development Builds** - <https://ci.lucko.me/job/ConditionalPerms/>

## Usage
ConditionalPerms works with any existing permissions plugin, and applies certain permissions only if a condition is met. There is no configuration. You setup your conditions using permission nodes.

For example, if you wanted to grant a user access to `essentials.fly`, but only in the `world_nether` world, you would give them the `cperms.in_world.world_nether.essentials.fly` permission.

If you wanted to grant them fly in all worlds except `world_nether`, you would set `cperms.!in_world.world_nether.essentials.fly`.

You can also chain nodes together, as they are applied recursively.
For example, granting access to `bans.banhammer` only when a user is in Creative mode and flying would be done using `cperms.is_flying.cperms.in_gamemode.creative.bans.banhammer`.

### Available conditions:
The general usage is `cperms.condition.parameter.your.node`, or if a parameter is not required, `cperms.condition.your.node`. You can negate permissions by adding a `!` to the start of the condition.

| Condition        | Description                  | Parameters       |
|------------------|------------------------------|------------------|
| in_gamemode      | If in a certain gamemode     | the gamemode     |
| in_world         | If in a certain world        | the world        |
| is_flying        | If flying                    | n/a              |
| in_region        | In in a WorldGuard region    | the region       |

More conditions coming soon. Feel free to PR new ones.

### Mode info
You can reload all online users (for example after you give a new permission) using the **/cperms reload** command. The permission needed to use this is **conditionalperms.reload**.