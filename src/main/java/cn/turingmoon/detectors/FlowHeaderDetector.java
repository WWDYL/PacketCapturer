package cn.turingmoon.detectors;

import cn.turingmoon.constants.AttackType;
import cn.turingmoon.constants.FlowType;
import cn.turingmoon.models.Flow;
import cn.turingmoon.utilities.RedisUtils;

public class FlowHeaderDetector {
    private boolean isSmall(int num) {
        /* TODO: implement this function. */
        return false;
    }

    private boolean isLarge(int num) {
        /* TODO: implement this function. */
        return false;
    }

    private boolean isReflectingPort(String sPort) {
        int port = Integer.parseInt(sPort);
        return port == 7 || port == 13 || port == 13 || port == 17;
    }

    private boolean isBroadcastAddr(String ip) {
        return false;
    }

    private void recordAttackType(Flow flow, AttackType type) {
        RedisUtils utils = RedisUtils.getInstance();

    }

    private void detect(Flow flow) {
        if (flow.getType().equals(FlowType.TCP)) {
            /* 检测是否是land attack */
            if (flow.getsIP().equals(flow.getdIP()) && flow.getsPort().equals(flow.getdPort())) {
                recordAttackType(flow, AttackType.Land);
            }
            /* 检测是否是TCP flooding */
            if (isLarge(flow.getpNum()) && isLarge(flow.getpSize())) {
                recordAttackType(flow, AttackType.TCP_flooding);
            }
        } else if (flow.getType().equals(FlowType.UDP)) {
            if (isReflectingPort(flow.getdPort())) {
                if (isReflectingPort(flow.getsPort())) {
                    recordAttackType(flow, AttackType.ping_pong);
                }
                if (isBroadcastAddr(flow.getdIP())) {
                    recordAttackType(flow, AttackType.Fraggle);
                }
            }
            if (isLarge(flow.getpNum()) && isLarge(flow.getpSize())) {
                recordAttackType(flow, AttackType.UDP_flooding);
            }
        } else if (flow.getType().equals(FlowType.ICMP_Echo)) {
            if (isBroadcastAddr(flow.getdIP())) {
                recordAttackType(flow, AttackType.Smurf);
            }
            if (isLarge(flow.getpSize() / flow.getpNum())) {
                recordAttackType(flow, AttackType.Ping_of_death);
            }
            if (isLarge(flow.getpSize()) && isLarge(flow.getpNum())) {
                recordAttackType(flow, AttackType.ICMP_flooding);
            }
        }
    }

    public void run() {

    }

}