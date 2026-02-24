#!/usr/bin/env python3
"""
Shooter Profile Rotating 3D Animation
======================================
Two modes via --surface flag:

  DEFAULT  — rotating scatter cloud + cyan optimal frontier line
  --surface — rotating solid surface mesh (shooting envelope shape)
             with a glowing wireframe grid drawn on top

Usage
-----
  # Scatter video (default)
  python tools/shooter_profile_animate.py

  # Surface mesh video
  python tools/shooter_profile_animate.py --surface

  # Custom duration / fps
  python tools/shooter_profile_animate.py --surface --fps 30 --duration 15

Output
------
  shooter_profile_scatter.mp4  or  shooter_profile_surface.mp4
"""

import sys, os, shutil, argparse
from typing import Optional
import numpy as np
import pandas as pd
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import matplotlib.animation as animation
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D  # noqa: F401

# ── Style ──────────────────────────────────────────────────────────────────────
DARK_BG  = "#0e1117"
PANEL_BG = "#1a1d27"
GRID_CLR = "#2a2d3a"
TEXT_CLR = "#e0e0e0"

# ─────────────────────────────────────────────────────────────────────────────
# Shared helpers
# ─────────────────────────────────────────────────────────────────────────────

def _style_ax(ax):
    ax.set_facecolor(PANEL_BG)
    ax.tick_params(colors=TEXT_CLR, labelsize=7)
    for pane in (ax.xaxis.pane, ax.yaxis.pane, ax.zaxis.pane):
        pane.fill = False
        pane.set_edgecolor(GRID_CLR)
    ax.xaxis.line.set_color(GRID_CLR)
    ax.yaxis.line.set_color(GRID_CLR)
    ax.zaxis.line.set_color(GRID_CLR)


def _find_ffmpeg() -> Optional[str]:
    """Return path to ffmpeg executable, checking PATH and common winget locations."""
    # 1 — on PATH already (works after terminal restart)
    found = shutil.which("ffmpeg")
    if found:
        return found

    # 2 — winget installs to %LOCALAPPDATA%\Microsoft\WinGet\Packages\Gyan.FFmpeg_*
    import glob
    local_app = os.environ.get("LOCALAPPDATA", "")
    patterns = [
        os.path.join(local_app,  "Microsoft", "WinGet", "Packages", "Gyan.FFmpeg_*", "**", "ffmpeg.exe"),
        os.path.join(local_app,  "Microsoft", "WinGet", "Links",    "ffmpeg.exe"),
        r"C:\ffmpeg\bin\ffmpeg.exe",
        r"C:\Program Files\ffmpeg\bin\ffmpeg.exe",
        r"C:\Program Files (x86)\ffmpeg\bin\ffmpeg.exe",
    ]
    for pat in patterns:
        hits = glob.glob(pat, recursive=True)
        if hits:
            return hits[0]
    return None


def _save(ani, out_path: str, fps: int, total_frames: int, dark_bg: str):
    """Save animation — keeps ani alive to avoid the 'deleted without rendering' warning."""
    print(f"  Rendering {total_frames} frames → {out_path}")

    ffmpeg_path = _find_ffmpeg()
    if ffmpeg_path:
        print(f"  Using ffmpeg: {ffmpeg_path}")
        matplotlib.rcParams["animation.ffmpeg_path"] = ffmpeg_path
        writer = animation.FFMpegWriter(
            fps=fps, bitrate=5000,
            metadata={"title": "Shooter Profile 3D"},
            extra_args=["-pix_fmt", "yuv420p"],
        )
        # Keep an explicit local ref so ani is not GC'd before save() finishes
        _ani_ref = ani
        _ani_ref.save(out_path, writer=writer, dpi=130,
                      savefig_kwargs={"facecolor": dark_bg})
        print(f"  Done → {out_path}")
    else:
        print("ERROR: ffmpeg not found. Open a NEW terminal (PATH not refreshed yet) and re-run,")
        print("  or run:  pip install imageio[ffmpeg]  and retry.")
        sys.exit(1)



def _cam_arrays(total_frames):
    az = np.linspace(0, 360, total_frames, endpoint=False)
    el = 18 + 14 * np.sin(np.linspace(0, 2 * np.pi, total_frames))
    return az, el


def _frontier(df: pd.DataFrame) -> pd.DataFrame:
    bins = np.arange(df["distance_m"].min(), df["distance_m"].max() + 0.25, 0.25)
    df   = df.copy()
    df["dist_bin"] = pd.cut(df["distance_m"], bins=bins, labels=bins[:-1])
    return (
        df.groupby("dist_bin", observed=True)
          .apply(lambda g: g.loc[g["velocity_mps"].idxmin()], include_groups=False)
          .reset_index(drop=True)
          .dropna(subset=["distance_m"])
          .sort_values("distance_m")
    )


# ─────────────────────────────────────────────────────────────────────────────
# MODE 1  —  Scatter cloud + frontier line
# ─────────────────────────────────────────────────────────────────────────────

def animate_scatter(df_full: pd.DataFrame, out_path: str, fps: int, duration_s: int):
    print("Mode: SCATTER CLOUD")
    sample = df_full if len(df_full) <= 120_000 else df_full.sample(120_000, random_state=42)
    front  = _frontier(df_full)

    fig = plt.figure(figsize=(14, 9), facecolor=DARK_BG)
    ax  = fig.add_subplot(111, projection="3d")
    _style_ax(ax)

    sc = ax.scatter(
        sample["distance_m"], sample["hood_angle_deg"], sample["velocity_mps"],
        c=sample["distance_m"], cmap="plasma",
        s=0.8, alpha=0.20, linewidths=0, rasterized=True,
    )
    ax.plot(
        front["distance_m"], front["hood_angle_deg"], front["velocity_mps"],
        color="#00ffe0", linewidth=2.5, label="Optimal frontier (min vel)", zorder=10,
    )

    cbar = fig.colorbar(sc, ax=ax, pad=0.10, shrink=0.55)
    cbar.set_label("Distance to Hub (m)", color=TEXT_CLR, fontsize=10)
    cbar.ax.yaxis.set_tick_params(color=TEXT_CLR)
    plt.setp(plt.getp(cbar.ax.axes, "yticklabels"), color=TEXT_CLR)

    ax.set_xlabel("Distance to Hub (m)",  color=TEXT_CLR, fontsize=10, labelpad=10)
    ax.set_ylabel("Hood Angle (°)",        color=TEXT_CLR, fontsize=10, labelpad=10)
    ax.set_zlabel("Launch Velocity (m/s)", color=TEXT_CLR, fontsize=10, labelpad=10)
    ax.set_title(
        "Shooter Parameter Space — Velocity vs Hood Angle vs Distance\n"
        "(cyan = optimal frontier)",
        color=TEXT_CLR, fontsize=13, pad=14,
    )
    ax.legend(loc="upper left", facecolor=PANEL_BG, labelcolor=TEXT_CLR, fontsize=9)
    plt.tight_layout()

    total_frames = fps * duration_s
    az, el = _cam_arrays(total_frames)

    def update(i):
        ax.view_init(elev=el[i], azim=az[i])
        return (sc,)

    ani = animation.FuncAnimation(fig, update, frames=total_frames,
                                   interval=1000/fps, blit=False)
    _save(ani, out_path, fps, total_frames, DARK_BG)
    plt.close()


# ─────────────────────────────────────────────────────────────────────────────
# MODE 2  —  Solid surface mesh (the "shape")
# ─────────────────────────────────────────────────────────────────────────────

def _build_surface_grid(df: pd.DataFrame):
    """
    Pivot (distance × hood_angle) → velocity, taking the MINIMUM velocity
    that scores for each cell. This produces the lower boundary surface —
    the "you must shoot at least this fast" envelope.
    NaN cells (no successful shot for that combo) are filled by interpolation.
    """
    # Round to grid resolution
    DIST_STEP  = 0.5   # m  — coarser for a cleaner mesh
    ANGLE_STEP = 2.0   # deg

    df = df.copy()
    df["dist_bin"]  = (df["distance_m"]    / DIST_STEP).round()  * DIST_STEP
    df["angle_bin"] = (df["hood_angle_deg"] / ANGLE_STEP).round() * ANGLE_STEP

    pivot = (
        df.groupby(["dist_bin", "angle_bin"])["velocity_mps"]
          .min()
          .unstack(level="angle_bin")
    )
    # Fill gaps with forward/backward interpolation along each axis
    pivot = pivot.interpolate(method="linear", axis=0).interpolate(method="linear", axis=1)
    pivot = pivot.ffill(axis=0).bfill(axis=0).ffill(axis=1).bfill(axis=1)

    D  = pivot.index.values.astype(float)          # distance values
    A  = pivot.columns.values.astype(float)        # angle values
    DD, AA = np.meshgrid(D, A, indexing="ij")
    VV = pivot.values                               # velocity surface

    return DD, AA, VV


def animate_surface(df_full: pd.DataFrame, out_path: str, fps: int, duration_s: int):
    print("Mode: SURFACE MESH  (building grid …)")
    DD, AA, VV = _build_surface_grid(df_full)
    front = _frontier(df_full)
    print(f"  Surface grid: {DD.shape[0]} × {DD.shape[1]} cells")

    fig = plt.figure(figsize=(14, 9), facecolor=DARK_BG)
    ax  = fig.add_subplot(111, projection="3d")
    _style_ax(ax)

    # ── Solid coloured surface ──────────────────────────────────────────────
    norm   = plt.Normalize(vmin=VV.min(), vmax=VV.max())
    colors = cm.plasma(norm(VV))
    surf = ax.plot_surface(
        DD, AA, VV,
        facecolors=colors,
        alpha=0.85,
        linewidth=0,
        antialiased=True,
        rasterized=True,
        shade=True,
    )

    # ── Wireframe overlay — draws the "connected dots" grid ─────────────────
    ax.plot_wireframe(
        DD, AA, VV,
        color="#ffffff",
        linewidth=0.4,
        alpha=0.25,
        rcount=40,   # number of row lines
        ccount=30,   # number of column lines
    )

    # ── Optimal frontier line on top ────────────────────────────────────────
    ax.plot(
        front["distance_m"], front["hood_angle_deg"], front["velocity_mps"],
        color="#00ffe0", linewidth=3.0, label="Optimal frontier (min vel)", zorder=20,
    )

    # ── Colorbar (maps surface colour → velocity) ───────────────────────────
    sm = plt.cm.ScalarMappable(cmap="plasma", norm=norm)
    sm.set_array([])
    cbar = fig.colorbar(sm, ax=ax, pad=0.10, shrink=0.55)
    cbar.set_label("Launch Velocity (m/s)", color=TEXT_CLR, fontsize=10)
    cbar.ax.yaxis.set_tick_params(color=TEXT_CLR)
    plt.setp(plt.getp(cbar.ax.axes, "yticklabels"), color=TEXT_CLR)

    ax.set_xlabel("Distance to Hub (m)",  color=TEXT_CLR, fontsize=10, labelpad=12)
    ax.set_ylabel("Hood Angle (°)",        color=TEXT_CLR, fontsize=10, labelpad=12)
    ax.set_zlabel("Launch Velocity (m/s)", color=TEXT_CLR, fontsize=10, labelpad=12)
    ax.set_title(
        "Shooter Envelope — Minimum Velocity Surface\n"
        "(surface = successful shooting boundary  |  cyan = optimal frontier)",
        color=TEXT_CLR, fontsize=13, pad=14,
    )
    ax.legend(loc="upper left", facecolor=PANEL_BG, labelcolor=TEXT_CLR, fontsize=9)
    plt.tight_layout()

    total_frames = fps * duration_s
    az, el = _cam_arrays(total_frames)

    def update(i):
        ax.view_init(elev=el[i], azim=az[i])
        return (surf,)

    ani = animation.FuncAnimation(fig, update, frames=total_frames,
                                   interval=1000/fps, blit=False)
    _save(ani, out_path, fps, total_frames, DARK_BG)
    plt.close()


# ─────────────────────────────────────────────────────────────────────────────
# Entry point
# ─────────────────────────────────────────────────────────────────────────────

def main():
    parser = argparse.ArgumentParser(description="Shooter Profile 3D Rotating Video")
    parser.add_argument("csv", nargs="?", default="shooter_profile.csv")
    parser.add_argument("--surface",  action="store_true",
                        help="Render solid surface mesh instead of scatter cloud")
    parser.add_argument("--fps",      type=int, default=30)
    parser.add_argument("--duration", type=int, default=12,
                        help="Video length in seconds (default 12)")
    args = parser.parse_args()

    if not os.path.isfile(args.csv):
        print(f"ERROR: '{args.csv}' not found. Run ./gradlew runProfiler first.")
        sys.exit(1)

    print(f"Loading {args.csv} …")
    df = pd.read_csv(args.csv)
    print(f"  {len(df):,} successful shots")

    out_dir = os.path.dirname(os.path.abspath(args.csv))

    if args.surface:
        out = os.path.join(out_dir, "shooter_profile_surface.mp4")
        animate_surface(df, out, args.fps, args.duration)
    else:
        out = os.path.join(out_dir, "shooter_profile_scatter.mp4")
        animate_scatter(df, out, args.fps, args.duration)


if __name__ == "__main__":
    main()
