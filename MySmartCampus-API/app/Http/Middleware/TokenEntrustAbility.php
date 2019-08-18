<?php

namespace App\Http\Middleware;

use Closure;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Exceptions\TokenExpiredException;
use Tymon\JWTAuth\Http\Middleware\BaseMiddleware;

class TokenEntrustAbility extends BaseMiddleware
{
    public function handle($request, Closure $next, $roles, $validateAll = false)
    {

        if (! $token = $this->auth->setRequest($request)->getToken()) {

            return response()->json(["error" => "token_not_provided"], 400);
        }

        try {
            $user = $this->auth->authenticate($token);
        } catch (TokenExpiredException $e) {
            return response()->json(["error" => "token_expired"], 400);
        } catch (JWTException $e) {
            return response()->json(["error" => "token_invalid"], 400);
        }

        if (! $user) {
            return response()->json(["error" => "user_not_found"], 400);
        }

        if (!$request->user()->ability(explode('|', $roles), array('validate_all' => $validateAll))) {
            return response()->json(["error" => "Unauthorized"], 400);
        }



        //$this->events->fire('tymon.jwt.valid', $user);

        return $next($request);
    }
}
