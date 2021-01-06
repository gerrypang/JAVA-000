-- 扣减库存Lua脚本
-- 库存（stock）-1：表示不限库存
-- 库存（stock）0：表示没有库存
-- 库存（stock）大于0：表示剩余库存
-- @params 库存key

-- exists 判断是否存在KEY，如果存在返回1，不存在返回0
if (redis.call('exists', KEYS[1]) == 1) then
	-- get 获取KEY的缓存值，tonumber 将redis数据转成 lua 的整形
    local stock = tonumber(redis.call('get', KEYS[1]));
    local num = tonumber(ARGV[1]);
    if (stock == -1) then
        return -1;
    end;
	-- incrby 特性进行库存的扣减
    if (stock >= num) then
        return redis.call('incrby', KEYS[1], 0-num);
    end;
   	return -2;
end;
return -3;