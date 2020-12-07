if (redis.call('hexists', KEYS[1], KEYS[2]) == 0) then
    return nil;
end;
local counter = redis.call('hincrby', KEYS[1], KEYS[2], -1);
if (counter > 0) then
    redis.call('pexpire', KEYS[1], ARGV[1]);
    return 0;
else
    redis.call('hdel', KEYS[1], KEYS[2]);
    return 1;
end;
return nil;